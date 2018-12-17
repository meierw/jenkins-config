import com.cloudbees.plugins.credentials.domains.Domain

print Jenkins.get().getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()
    .getCredentials(Domain.global()) ? true : false