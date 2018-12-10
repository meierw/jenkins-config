jenkins_config
==============

Ansible role which manages various options/configurations in Jenkins.

Requirements
------------

* A Jenkins service plus any plugin that you plan to configure.
* The ability to execute [jenkins_script](https://docs.ansible.com/ansible/2.5/modules/jenkins_script_module.html) against said Jenkins service.

Role Variables
--------------

_IMPORTANT:_ When specified, most of these variables will overwrite previously set Jenkins options.
Be mindful that you don't lose any setting, that you have previously set up.

```yaml
jenkins_config_url: http://localhost:8080
jenkins_config_user: admin
jenkins_config_password: admin
```
The URL, username and password for authenticating with Jenkins. Will be used to execute `jenkins_script`.

```yaml
jenkins_config_csrf_enabled: null
# Possible values:
#   true
#   false
```
CSRF Protection state to set in Jenkins settings.
Set doesn't happen, unless one of the possible values is specified.

```yaml
jenkins_config_global_environment_variables: []
# jenkins_config_global_environment_variables:
#   - name: FOO
#     value: bar
#   - name: TOKEN
#     value: xxxyyyzzz
```
Jenkins Global Environment variables to set in Jenkins settings.
Set doesn't happen if list is empty.

```yaml
jenkins_config_pipeline_durability: ''
# Possible values:
#   MAX_SURVIVABILITY
#   PERFORMANCE_OPTIMIZED
#   SURVIVABLE_NONATOMIC
```
Pipeline Default Speed/Durability Level to set in Jenkins settings.
Set doesn't happen, unless one of the possible values is specified.

```yaml
jenkins_config_simple_theme_css_url: ''
# jenkins_config_simple_theme_css_url: https://cdn.rawgit.com/afonsof/jenkins-material-theme/gh-pages/dist/material-indigo.css
```
URL of theme CSS to set in Jenkins Simple Theme settings.
Set doesn't happen, if this value is empty.

```yaml
jenkins_config_jenkins_url: ''
```
Jenkins URL to set in Jenkins settings.
Set doesn't happen, if this value is empty.

```yaml
jenkins_config_system_admin_email: ''
```
System Admin e-mail address to set in Jenkins settings.
Set doesn't happen, if this value is empty.

```yaml
jenkins_config_global_pipeline_libraries: []
# jenkins_config_global_pipeline_libraries:
#   - name: global-pipeline-library
#     default_version: master
#     load_implicitly: false
#     allow_default_version_override: true
#     scm_git_path: 'git@github.com:example/global-pipeline-library.git'
#     scm_credentials_id: jenkins-ssh
```
Global Pipeline Libraries to set in Jenkins settings.
Each library field is mandatory (can not be null).
Fields `name|scm_git_path|scm_credentials_id` can not be empty strings.
Set doesn't happen if list is empty.

```yaml
jenkins_config_email_notification: null
# jenkins_config_email_notification:
#   smtp_server: smtp.gmail.com
#   default_user_email_suffix: @example.com
#   smtp_auth_username: example
#   smtp_auth_password: "{{ secret_smtp_password }}"
#   use_ssl: false
#   smtp_port: 465
#   reply_to_address: ''
#   charset: UTF-8
```
E-mail Notification values to set in Jenkins settings.
Set doesn't happen, if `jenkins_config_email_notification.smtp_server is undefined`.
Set unneeded values as `''` to avoid undefined errors during execution.

```yaml
jenkins_config_credentials: []
# jenkins_config_credentials:
#   - kind: ssh_username_with_private_key
#     id: jenkins-ssh
#     username: jenkins
#     key_value: "{{ secret_key }}"
#     key_passphrase: ''
#     description: Key added from Ansible
```
Credentials to set in the Global scope.
Each credential field is mandatory (can not be null).
Fields `id|username|key_value` can not be empty strings.
Set doesn't happen if list is empty.

Supported credential `kind`:
* `ssh_username_with_private_key`

Example Playbook
----------------

```yaml
- hosts: servers
  roles:
    - { role: meierw.jenkins_config }
```

License
-------

MIT

Author Information
------------------

* _Author:_ [Walter Meier](mailto:valters.meirens@gmail.com)
