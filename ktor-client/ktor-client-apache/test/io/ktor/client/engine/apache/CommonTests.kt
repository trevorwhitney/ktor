package io.ktor.client.engine.apache

import io.ktor.client.engine.*
import io.ktor.client.tests.*
import org.junit.*


@Ignore
class ApacheCacheTest : CacheTest(Apache)

class ApacheCookiesTest : CookiesTest(Apache)

class ApachePostTest : PostTest(Apache.config {
    socketTimeout = 100_000
})

class ApacheMultithreadedTest : MultithreadedTest(Apache)

class ApacheFullFormTest : FullFormTest(Apache)

class ApacheContentTest : ContentTest(Apache)

class ApacheRedirectTest : HttpRedirectTest(Apache)

class ApacheBuildersTest : BuildersTest(Apache)

class ApacheFeaturesTest : FeaturesTest(Apache)
