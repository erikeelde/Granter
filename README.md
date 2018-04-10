# Granter
Build status on develop: ![Build status on develop](https://travis-ci.org/erikeelde/Granter.svg?branch=develop)

#### Publish Locally:
./gradlew :granter:publishToMavenLocal

#### Publish to artifactory:
./gradlew :granter:publish -Partifactory_user=USER -Partifactory_password=PASSWORD -Partifactory_url=https://my.artifactory/repository

or set the values in gradle.properties
