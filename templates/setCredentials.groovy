import jenkins.model.Jenkins
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey

def credentialsStore = Jenkins.get().getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()
def currentCredentials = credentialsStore.getCredentials(Domain.global())
currentCredentials.each {
    credentialsStore.removeCredentials(Domain.global(), it)
}

{% for cred in jenkins_config_credentials %}
    {% if cred.id != '' and cred.username != '' and cred.key_value != ''%}
        credentialsStore.addCredentials(
            Domain.global(),
            new BasicSSHUserPrivateKey(
                CredentialsScope.GLOBAL,
                '{{ cred.id }}',
                '{{ cred.username }}',
                new BasicSSHUserPrivateKey.DirectEntryPrivateKeySource("{{ cred.key_value | replace('\n', '\\n') }}"),
                '{{ cred.key_passphrase }}',
                '{{ cred.description }}'
            )
        )
    {% endif%}
{% endfor %}