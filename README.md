# spring-unicore

[![Build Status](https://travis-ci.org/unicore-life/spring-unicore.svg)](https://travis-ci.org/unicore-life/spring-unicore)
[![Coverage Status](https://coveralls.io/repos/unicore-life/spring-unicore/badge.svg?branch=master&service=github)](https://coveralls.io/github/unicore-life/spring-unicore?branch=master)
[![Read The Docs](https://readthedocs.org/projects/spring-unicore/badge/?version=latest)](http://spring-unicore.readthedocs.org)
[![Download](https://api.bintray.com/packages/unicore-life/maven/spring-unicore/images/download.svg)](https://bintray.com/unicore-life/maven/spring-unicore/_latestVersion)
[![Stories in Ready](https://badge.waffle.io/unicore-life/spring-unicore.svg?label=ready&title=Ready)](http://waffle.io/unicore-life/spring-unicore)

## Development

### Building

Just clone the project and run Gradle command presented below.

```bash
./gradlew build
```

### Releasing

To see current version of the module sources use Gradle task
[currentVersion](http://axion-release-plugin.readthedocs.io/en/latest/configuration/tasks.html#currentversion)
(it is stored as a git tag).

```bash
./gradlew :unicore-spring-boot-starter:currentVersion
```

To release a new version use
[release](http://axion-release-plugin.readthedocs.io/en/latest/configuration/tasks.html#release) task.
Later, for uploading artifact to [Bintray](https://dl.bintray.com/unicore-life/maven) maven repository
use [bintrayUpload](https://github.com/novoda/bintray-release) task.
Sample command are presented below.

```
./gradlew :unicore-spring-boot-starter:release
./gradlew :unicore-spring-boot-starter:bintrayUpload -PdryRun=false
```

Remember to configure [Bintray](https://bintray.com) user and key by using parameters
`-PbintrayUser=BINTRAY_USERNAME -PbintrayKey=BINTRAY_KEY` or just put them in `gradle.properties` file.


# Links

* [bintray-release](http://github.com/novoda/bintray-release)
