print Jenkins.get().getGlobalNodeProperties()
    .find { it.class == hudson.slaves.EnvironmentVariablesNodeProperty } ? true : false