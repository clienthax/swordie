@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  swordie startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and SWORDIE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\swordie-1.77.3.jar;%APP_HOME%\lib\javax.persistence-api-2.2.jar;%APP_HOME%\lib\mysql-connector-j-9.0.0.jar;%APP_HOME%\lib\netty-all-4.1.112.Final.jar;%APP_HOME%\lib\hibernate-core-6.5.2.Final.jar;%APP_HOME%\lib\jaxb-impl-4.0.5.jar;%APP_HOME%\lib\jaxb-core-4.0.5.jar;%APP_HOME%\lib\jaxb-runtime-4.0.2.jar;%APP_HOME%\lib\jaxb-core-4.0.2.jar;%APP_HOME%\lib\jakarta.xml.bind-api-4.0.2.jar;%APP_HOME%\lib\jython-standalone-2.7.3.jar;%APP_HOME%\lib\nashorn-core-15.4.jar;%APP_HOME%\lib\log4j-1.2.17.jar;%APP_HOME%\lib\jbcrypt-0.4.jar;%APP_HOME%\lib\protobuf-java-4.26.1.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.1.112.Final-linux-x86_64.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.1.112.Final-linux-aarch_64.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.1.112.Final-linux-riscv64.jar;%APP_HOME%\lib\netty-transport-native-kqueue-4.1.112.Final-osx-x86_64.jar;%APP_HOME%\lib\netty-transport-native-kqueue-4.1.112.Final-osx-aarch_64.jar;%APP_HOME%\lib\netty-transport-classes-epoll-4.1.112.Final.jar;%APP_HOME%\lib\netty-transport-classes-kqueue-4.1.112.Final.jar;%APP_HOME%\lib\netty-resolver-dns-native-macos-4.1.112.Final-osx-x86_64.jar;%APP_HOME%\lib\netty-resolver-dns-native-macos-4.1.112.Final-osx-aarch_64.jar;%APP_HOME%\lib\netty-resolver-dns-classes-macos-4.1.112.Final.jar;%APP_HOME%\lib\netty-resolver-dns-4.1.112.Final.jar;%APP_HOME%\lib\netty-handler-4.1.112.Final.jar;%APP_HOME%\lib\netty-transport-native-unix-common-4.1.112.Final.jar;%APP_HOME%\lib\netty-codec-dns-4.1.112.Final.jar;%APP_HOME%\lib\netty-codec-4.1.112.Final.jar;%APP_HOME%\lib\netty-transport-4.1.112.Final.jar;%APP_HOME%\lib\netty-buffer-4.1.112.Final.jar;%APP_HOME%\lib\netty-codec-haproxy-4.1.112.Final.jar;%APP_HOME%\lib\netty-codec-http-4.1.112.Final.jar;%APP_HOME%\lib\netty-codec-http2-4.1.112.Final.jar;%APP_HOME%\lib\netty-codec-memcache-4.1.112.Final.jar;%APP_HOME%\lib\netty-codec-mqtt-4.1.112.Final.jar;%APP_HOME%\lib\netty-codec-redis-4.1.112.Final.jar;%APP_HOME%\lib\netty-codec-smtp-4.1.112.Final.jar;%APP_HOME%\lib\netty-codec-socks-4.1.112.Final.jar;%APP_HOME%\lib\netty-codec-stomp-4.1.112.Final.jar;%APP_HOME%\lib\netty-codec-xml-4.1.112.Final.jar;%APP_HOME%\lib\netty-resolver-4.1.112.Final.jar;%APP_HOME%\lib\netty-common-4.1.112.Final.jar;%APP_HOME%\lib\netty-handler-proxy-4.1.112.Final.jar;%APP_HOME%\lib\netty-handler-ssl-ocsp-4.1.112.Final.jar;%APP_HOME%\lib\netty-transport-rxtx-4.1.112.Final.jar;%APP_HOME%\lib\netty-transport-sctp-4.1.112.Final.jar;%APP_HOME%\lib\netty-transport-udt-4.1.112.Final.jar;%APP_HOME%\lib\angus-activation-2.0.2.jar;%APP_HOME%\lib\jakarta.activation-api-2.1.3.jar;%APP_HOME%\lib\jakarta.persistence-api-3.1.0.jar;%APP_HOME%\lib\jakarta.transaction-api-2.0.1.jar;%APP_HOME%\lib\jboss-logging-3.5.0.Final.jar;%APP_HOME%\lib\hibernate-commons-annotations-6.0.6.Final.jar;%APP_HOME%\lib\jandex-3.1.2.jar;%APP_HOME%\lib\classmate-1.5.1.jar;%APP_HOME%\lib\byte-buddy-1.14.15.jar;%APP_HOME%\lib\jakarta.inject-api-2.0.1.jar;%APP_HOME%\lib\antlr4-runtime-4.13.0.jar;%APP_HOME%\lib\asm-commons-7.3.1.jar;%APP_HOME%\lib\asm-util-7.3.1.jar;%APP_HOME%\lib\asm-analysis-7.3.1.jar;%APP_HOME%\lib\asm-tree-7.3.1.jar;%APP_HOME%\lib\asm-7.3.1.jar;%APP_HOME%\lib\txw2-4.0.2.jar;%APP_HOME%\lib\istack-commons-runtime-4.1.1.jar


@rem Execute swordie
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %SWORDIE_OPTS%  -classpath "%CLASSPATH%" net.swordie.ms.Server %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable SWORDIE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%SWORDIE_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega