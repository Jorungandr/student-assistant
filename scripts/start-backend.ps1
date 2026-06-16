param(
    [string]$DbUsername = "root",
    [string]$DbPassword = "jorungandr"
)

$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot
$backend = Join-Path $root "backend"

$env:DB_USERNAME = $DbUsername
$env:DB_PASSWORD = $DbPassword

Set-Location $backend
mvn spring-boot:run

