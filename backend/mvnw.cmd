@REM ----------------------------------------------------------------------------
@REM Maven Start Up Batch script
@REM ----------------------------------------------------------------------------

@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET __MVNW_CMD__=
@SET __MVNW_ERROR__=
@SETLOCAL ENABLEEXTENSIONS ENABLEDELAYEDEXPANSION

@SET "MAVEN_PROJECTBASEDIR=%~dp0"
@IF "%MAVEN_PROJECTBASEDIR:~-1%"=="\" SET "MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%"

@SET "WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
@SET "WRAPPER_PROP=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties"

@IF EXIST "%WRAPPER_JAR%" GOTO runJar

@WHERE mvn >nul 2>nul
@IF %ERRORLEVEL% equ 0 (
  mvn %*
  @EXIT /B %ERRORLEVEL%
)

@echo Error: maven-wrapper.jar not found and mvn not on PATH.
@EXIT /B 1

:runJar
@SET "JAVA_EXE=java.exe"
@IF NOT "%JAVA_HOME%"=="" (
  @SET "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
)

"%JAVA_EXE%" %JVM_CONFIG_MAVEN_PROPS% -jar "%WRAPPER_JAR%" %*
@EXIT /B %ERRORLEVEL%
