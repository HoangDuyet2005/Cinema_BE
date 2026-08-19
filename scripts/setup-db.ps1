param(
    [string]$MysqlUser = "root",
    [string]$MysqlPass = "",
    [string]$DbName = "cinema2"
)

Write-Host "Creating database '$DbName' and importing seed files..."

$createDb = "CREATE DATABASE IF NOT EXISTS $DbName CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
$tmpSql = "temp_create_db.sql"
Set-Content -Path $tmpSql -Value $createDb

& mysql -u $MysqlUser -p$MysqlPass < $tmpSql
Remove-Item $tmpSql -ErrorAction SilentlyContinue

$seedFiles = @("seed_data.sql","seed_news.sql","seed_promotions.sql")
foreach ($f in $seedFiles) {
    if (Test-Path $f) {
        Write-Host "Importing $f..."
        & mysql -u $MysqlUser -p$MysqlPass $DbName < $f
    } else {
        Write-Warning "Seed file $f not found in repo root."
    }
}

Write-Host "Done."
