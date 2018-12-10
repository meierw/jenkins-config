import org.jenkinsci.plugins.workflow.libs.GlobalLibraries
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever
import jenkins.plugins.git.GitSCMSource

List<LibraryConfiguration> libConfigs = []

LibraryConfiguration libConfig = null
{% for library in jenkins_config_global_pipeline_libraries %}
    {% if library.name != '' and library.scm_git_path != '' and library.scm_credentials_id != '' %}
        libConfig = new LibraryConfiguration(
                '{{ library.name }}',
                new SCMSourceRetriever(new GitSCMSource(
                        '{{ library.name }}',
                        '{{ library.scm_git_path }}',
                        '{{ library.scm_credentials_id }}',
                        null, null, false
                ))
        )
        libConfig.setDefaultVersion('{{ library.default_version }}')
        libConfig.setImplicit({% if library.load_implicitly %}true{% else %}false{% endif %})
        libConfig.setAllowVersionOverride({% if library.allow_default_version_override %}true{% else %}false{% endif %})

        libConfigs.add(libConfig)
    {% endif %}
{% endfor %}

GlobalLibraries.get().setLibraries(libConfigs)