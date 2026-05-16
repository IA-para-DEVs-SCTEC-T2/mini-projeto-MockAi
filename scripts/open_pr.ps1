#Requires -Version 5.1
$ErrorActionPreference = "Stop"

# Validação: alterações não commitadas
$diffOutput = git diff --quiet 2>&1
$diffCachedOutput = git diff --cached --quiet 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "⛔ OPERAÇÃO BLOQUEADA: Existem alterações não commitadas. Por favor, realize o commit das alterações antes de executar o push."
    exit 1
}

$branch = git branch --show-current

# Validação: branch protegida
if ($branch -eq "main" -or $branch -eq "develop") {
    Write-Host "⛔ OPERAÇÃO BLOQUEADA: Push direto na branch '$branch' é proibido. Use uma branch de feature, bugfix ou docs."
    exit 1
}

# Extração do ISSUE_ID: padrão feature/task<N> ou bugfix/task<N>
if ($branch -match '^[a-zA-Z]+/task(\d+)') {
    $issueId = $Matches[1]
} else {
    Write-Host "Erro: não foi possível identificar o número da Issue pela branch."
    Write-Host "Use o padrão: feature/task6, feature/task6-descricao, bugfix/task6 ou bugfix/task6-descricao"
    exit 1
}

$issueTitle = gh issue view $issueId --json title --jq '.title'

$prTitle = $issueTitle -replace '\[(STORY|EPIC|DOCS|TECH|BUG)\]', '' -replace '^\s+', ''

$commitType = "feat"
if ($branch -like "docs/*") {
    $commitType = "docs"
} elseif ($branch -like "bugfix/*") {
    $commitType = "fix"
}

$bodyFile = [System.IO.Path]::GetTempFileName()

$bodyContent = @"
## O que foi feito

Implementação relacionada à Issue #$issueId.

## Issue relacionada

Closes #$issueId

## Validação da Issue

- [ ] Todos os itens aplicáveis do checklist técnico da Issue foram concluídos
- [ ] Os critérios BDD foram considerados, quando aplicável
- [ ] A Issue relacionada está pronta para ser fechada

## Checklist

- [ ] A User Story foi considerada, quando aplicável
- [ ] O código foi testado, quando aplicável
- [ ] A documentação foi atualizada, quando necessário
"@

Set-Content -Path $bodyFile -Value $bodyContent -Encoding UTF8

git push -u origin $branch

gh pr create `
    --base main `
    --head $branch `
    --title "${commitType}: $prTitle" `
    --body-file $bodyFile

Remove-Item -Path $bodyFile -Force
