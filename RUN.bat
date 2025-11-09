@echo off
title Flight Reservation System - Auto Deploy
color 0A

echo.
echo ====================================================
echo    Flight Reservation System - Auto Deploy
echo ====================================================
echo.

REM Step 1: Check Java
echo [1/5] Checking Java installation...
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    color 0C
    echo ERROR: Java is not installed!
    echo Please install Java JDK 11 from: https://adoptium.net/
    pause
    exit /b 1
)
echo [OK] Java found!

REM Step 2: Check Maven
echo [2/5] Checking Maven installation...
mvn -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    color 0C
    echo ERROR: Maven is not installed!
    echo Please install Maven from: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)
echo [OK] Maven found!

REM Step 3: Build Project
echo [3/5] Building project...
echo This may take a minute...
call mvn clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    color 0C
    echo ERROR: Build failed! Check errors above.
    pause
    exit /b 1
)
echo [OK] Build successful!

REM Step 4: Find and Deploy to Tomcat
echo [4/5] Searching for Tomcat...

set TOMCAT_FOUND=0
set WEBAPPS_PATH=

REM Check common Tomcat locations
if exist "C:\xampp\tomcat\webapps" (
    set WEBAPPS_PATH=C:\xampp\tomcat\webapps
    set TOMCAT_FOUND=1
    echo [OK] Found XAMPP Tomcat
)

if exist "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps" (
    set WEBAPPS_PATH=C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps
    set TOMCAT_FOUND=1
    echo [OK] Found Tomcat 9.0
)

if exist "C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps" (
    set WEBAPPS_PATH=C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps
    set TOMCAT_FOUND=1
    echo [OK] Found Tomcat 10.0
)

if exist "C:\apache-tomcat-9.0\webapps" (
    set WEBAPPS_PATH=C:\apache-tomcat-9.0\webapps
    set TOMCAT_FOUND=1
    echo [OK] Found Tomcat
)

if %TOMCAT_FOUND%==0 (
    echo.
    echo Tomcat not found in common locations.
    echo Please enter your Tomcat webapps folder path:
    echo Example: C:\apache-tomcat-9.0\webapps
    echo.
    set /p WEBAPPS_PATH="Enter webapps path: "
)

REM Verify path exists
if not exist "%WEBAPPS_PATH%" (
    color 0C
    echo ERROR: Invalid webapps path!
    pause
    exit /b 1
)

echo Deploying to: %WEBAPPS_PATH%
copy /Y target\flight-reservation-system.war "%WEBAPPS_PATH%\"
if %ERRORLEVEL% NEQ 0 (
    color 0C
    echo ERROR: Failed to copy WAR file!
    pause
    exit /b 1
)
echo [OK] Deployed successfully!

REM Step 5: Start Tomcat (if XAMPP)
echo [5/5] Starting Tomcat...
if exist "C:\xampp\tomcat_start.bat" (
    echo Starting XAMPP Tomcat...
    start "" "C:\xampp\tomcat_start.bat"
    timeout /t 5 /nobreak >nul
) else (
    echo Please start Tomcat manually:
    echo - Open XAMPP Control Panel and click Start on Tomcat
    echo - OR Open Windows Services and start Apache Tomcat service
    echo - OR Run startup.bat from Tomcat's bin folder
    echo.
    echo Press any key after starting Tomcat...
    pause >nul
)

REM Wait for deployment
echo.
echo Waiting for application to deploy...
timeout /t 10 /nobreak

REM Open browser
echo.
echo ====================================================
echo          DEPLOYMENT COMPLETED SUCCESSFULLY!
echo ====================================================
echo.
echo Opening browser in 3 seconds...
echo.
echo URL: http://localhost:8080/flight-reservation-system/
echo.
echo Admin Login:
echo Username: admin
echo Password: admin123
echo ====================================================
timeout /t 3 /nobreak >nul

start http://localhost:8080/flight-reservation-system/

echo.
echo If browser doesn't open automatically, copy this URL:
echo http://localhost:8080/flight-reservation-system/
echo.
echo Press any key to exit...
pause >nul
