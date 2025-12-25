<#
Simple demo script that calls the running Spring Boot app to:
 1) Register a user
 2) Login and print the JWT
 3) Create a restaurant
 4) List restaurants

Usage: Start the Spring Boot app (bootRun) in one terminal, then in another run:
  .\run-demo.ps1

This script expects the server at http://localhost:8080
#>

$base = 'http://localhost:8080'

function Post-Json($url, $body) {
    try {
        $json = $body | ConvertTo-Json -Depth 10
        return Invoke-RestMethod -Uri $url -Method Post -Body $json -ContentType 'application/json'
    } catch {
        Write-Host "ERROR calling $url`n$($_.Exception.Message)" -ForegroundColor Red
        return $null
    }
}

Write-Host "1) Registering user 'demo'..."
$reg = Post-Json "$base/api/auth/register" @{ username = 'demo'; password = 'demo' }
Write-Host "Register response:`n" $reg

Write-Host "`n2) Logging in to obtain JWT..."
$login = Post-Json "$base/api/auth/login" @{ username = 'demo'; password = 'demo' }
if ($null -eq $login) { Write-Host "Login failed" -ForegroundColor Red; exit 1 }
$token = $login.token
Write-Host "Token:`n$token`n" -ForegroundColor Green

Write-Host "3) Creating a restaurant (requires Authorization header)..."
$restaurant = @{ name='Demo Pizzeria'; address='123 Main St'; menu = @( @{ name='Margherita'; description='Tomato & cheese'; price=8.5 } ) }
try {
    $json = $restaurant | ConvertTo-Json -Depth 10
    $create = Invoke-RestMethod -Uri "$base/api/restaurants" -Method Post -Body $json -ContentType 'application/json' -Headers @{ Authorization = "Bearer $token" }
    Write-Host "Create response:`n" $create
} catch {
    Write-Host "Create failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n4) Listing restaurants..."
try {
    $list = Invoke-RestMethod -Uri "$base/api/restaurants" -Method Get -Headers @{ Authorization = "Bearer $token" }
    Write-Host ($list | ConvertTo-Json -Depth 10)
} catch {
    Write-Host "List failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "\nDemo finished. If you want verbose logs, check the terminal running bootRun. Press Ctrl+C in that terminal to stop the server."
