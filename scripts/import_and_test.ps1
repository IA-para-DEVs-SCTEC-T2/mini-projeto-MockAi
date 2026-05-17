$ErrorActionPreference = 'Stop'

$proc = Start-Process -FilePath ".\mvnw.cmd" -ArgumentList "spring-boot:run","-DskipTests" -NoNewWindow -PassThru
$mvnPid = $proc.Id
$mvnPid | Out-File mvn_pid.txt -Encoding ascii
Write-Output "Started mvnw (pid=$mvnPid)"

$max = 60
$i = 0
while ($i -lt $max) {
    try {
        $r = Invoke-WebRequest -Uri 'http://localhost:8080/mockai/v3/api-docs' -UseBasicParsing -TimeoutSec 2
        if ($r.StatusCode -eq 200) { break }
    } catch {
        # ignore
    }
    Start-Sleep -s 1
    $i++
}
if ($i -ge $max) {
    Write-Error 'Server did not start in time'
    Stop-Process -Id $pid -Force
    exit 1
}

Write-Output 'Server ready, importing spec...'
Invoke-RestMethod -Uri 'http://localhost:8080/mockai/import' -Method Post -Form @{ file = Get-Item 'docs/petstore-enriched.json' }
Write-Output 'Import completed'

$pets = Invoke-RestMethod -Uri 'http://localhost:8080/mockai/pets' -Method Get
$pets | ConvertTo-Json -Depth 10 | Out-File pet_response.json -Encoding utf8
Write-Output 'Pets response saved to pet_response.json'
Get-Content pet_response.json -Raw

Stop-Process -Id $mvnPid -Force
Write-Output "Stopped mvnw (pid=$mvnPid)"
