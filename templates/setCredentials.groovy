import jenkins.model.Jenkins
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl
import com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey

def credentialsStore = Jenkins.get().getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()
def currentCredentials = credentialsStore.getCredentials(Domain.global())
currentCredentials.each {
    credentialsStore.removeCredentials(Domain.global(), it)
}

{% for cred in jenkins_config_credentials %}
    {% if cred.id != ''
        and cred.kind in ['username_with_password', 'ssh_username_with_private_key']
    %}
        credentialsStore.addCredentials(
            Domain.global(),
            {% if cred.kind == 'username_with_password' %}
                new UsernamePasswordCredentialsImpl(
                    CredentialsScope.GLOBAL,
                    '{{ cred.id }}',
                    '{{ cred.description }}',
                    '{{ cred.username }}',
                    '{{ cred.password }}'
                )
            {% elif cred.kind == 'ssh_username_with_private_key' %}
                new BasicSSHUserPrivateKey(
                    CredentialsScope.GLOBAL,
                    '{{ cred.id }}',
                    '{{ cred.username }}',
                    new BasicSSHUserPrivateKey.DirectEntryPrivateKeySource('{{ cred.private_key | replace('\n', '\\n') }}'),
                    '{{ cred.passphrase }}',
                    '{{ cred.description }}'
                )
            {% endif %}
        )
    {% endif %}
{% endfor %}