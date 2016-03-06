#!/usr/bin/env bash

keytool -genkey -keyalg RSA -keystore keystore.jks -storepass password -alias spring-unicore -dname "CN=spring/unicore.eu,OU=ICM,O=UW,C=PL" -validity 4000 -keysize 4096
