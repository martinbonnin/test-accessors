package com.example

import okio.Okio
import com.gradleup.gr8.Gr8Plugin

val hello = "world $Okio ${Gr8Plugin::class.java}"