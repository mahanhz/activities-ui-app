#!/bin/sh

echo "Passed in arguments are: $1, $2"
semanticVersionUpdate=$1
fallbackReleaseVersion=$2
snapshotSuffix="-SNAPSHOT"

# Fetch the latest version from Nexus
nexusMetadata=`curl -s -L "http://192.168.1.31:8082/nexus/service/local/repositories/releases/content/com/amhzing/activities/ui/activities-ui/maven-metadata.xml"`
versionToRelease=`echo $nexusMetadata | grep -oP '(?<=<version>)[^<]+' | sort -nk1,1 | tail -1`

echo "Latest version from Nexus: $versionToRelease"

if [ -z "$versionToRelease" ]
then
    echo "Could not find a version in Nexus"
    echo "Fallback to release version (from properties file): $fallbackReleaseVersion"
    versionToRelease=$fallbackReleaseVersion
    semanticVersionUpdate="unchanged"
fi

major=`echo $versionToRelease | cut -d. -f1`
minor=`echo $versionToRelease | cut -d. -f2`
patch=`echo $versionToRelease | cut -d. -f3`

echo "major=$major, minor=$minor, patch=$patch"

if [ $semanticVersionUpdate = "unchanged" ]; then
	echo "Unchanged"
elif [ $semanticVersionUpdate = "major" ]; then
	major=$((major+1))
	minor=0
	patch=0
elif [ $semanticVersionUpdate = "minor" ]; then
	minor=$((minor+1))
	patch=0
elif [ $semanticVersionUpdate = "patch" ]; then
    patch=$((patch+1))
else
	echo "Invalid semantic version update: $semanticVersionUpdate"
fi

versionToRelease=$major.$minor.$patch
nextVersion=$major.$minor.$((patch+1))$snapshotSuffix

echo "Release version after semantic update: $versionToRelease"
echo "Next version: $nextVersion"

./gradlew release -Prelease.useAutomaticVersion=true -Prelease.releaseVersion=$versionToRelease -Prelease.newVersion=$nextVersion