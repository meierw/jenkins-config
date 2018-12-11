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

-------
```yaml
jenkins_config_url: http://localhost:8080
jenkins_config_user: admin
jenkins_config_password: admin
```
The URL, username and password for authenticating with Jenkins. Will be used to execute `jenkins_script`.

-------
```yaml
jenkins_config_csrf_enabled: null
```
CSRF Protection state to set in Jenkins settings.
Set doesn't happen, unless one of the supported values is specified.

Supported values:
* `true`
* `false`

-------
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

-------
```yaml
jenkins_config_pipeline_durability: ''
```
Pipeline Default Speed/Durability Level to set in Jenkins settings.
Set doesn't happen, unless one of the supported values is specified.

Supported values:
* `max_survivability`
* `performance_optimized`
* `survivable_nonatomic`

-------
```yaml
jenkins_config_simple_theme_css_url: ''
# jenkins_config_simple_theme_css_url: https://cdn.rawgit.com/afonsof/jenkins-material-theme/gh-pages/dist/material-indigo.css
```
URL of theme CSS to set in Jenkins Simple Theme settings.
Set doesn't happen, if this value is empty.

-------
```yaml
jenkins_config_jenkins_url: ''
```
Jenkins URL to set in Jenkins settings.
Set doesn't happen, if this value is empty.

-------
```yaml
jenkins_config_system_admin_email: ''
```
System Admin e-mail address to set in Jenkins settings.
Set doesn't happen, if this value is empty.

-------
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
All fields should contain a non-null value (empty string/list or 0 depending on the type).
Set doesn't happen if list is empty.

Mandatory fields (cannot be empty):
* `name`
* `scm_git_path`
* `scm_credentials_id`

-------
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
All fields should contain a non-null value (empty string/list or 0 depending on the type).
Set doesn't happen, if `jenkins_config_email_notification.smtp_server is undefined`.

-------
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
All fields should contain a non-null value (empty string/list or 0 depending on the type).
Set doesn't happen if list is empty.

Mandatory fields (cannot be empty):
* `kind`
* `id`

Supported `kind` and extra fields needed for it:
* `username_with_password`
    * `id` : string
    * `username` : string
    * `password` : string
    * `description` : string
* `ssh_username_with_private_key`
    * `id` : string
    * `username` : string
    * `private_key` : string
    * `passphrase` : string
    * `description` : string

-------
```yaml
jenkins_config_nodes: []
# jenkins_config_nodes:
#   - name: slave0
#     description: Node added from Ansible
#     num_of_executors: 2
#     remote_root_directory: /var/lib/jenkins
#     labels: labels separated by spaces
#     usage: exclusive
#     launch_method:
#       type: via_ssh
#       host: slave0.example.com
#       port: 22
#       credentials_id: jenkins-ssh
#       verification_strategy:
#         type: manually_trusted_key
#         require_initial_manual_trust: true
#     availability:
#       type: always
#     environment_variables: # optional definition
#       - name: FOO
#         value: bar
```
Nodes to set in Jenkins.
All fields which are defined should contain a non-null value (empty string/list or 0 depending on the type).
Set doesn't happen if list is empty.

Mandatory fields (cannot be empty):
* `name`
* `num_of_executors`
* `remote_root_directory`
* `usage`
* `launch_method`
* `availability`

Supported `usage` values:
* `normal`
* `exclusive`

Supported `launch_method` : `type` and extra fields needed for it:
* `via_command`
    * `command` : string
* `via_ssh`
    * `host` : string
    * `port` : integer
    * `credentials_id` : string
    * Supported `verification_strategy` : `type` and extra fields needed for it:
        * `known_hosts_file`
        * `manually_provided_key`
            * `ssh_key` : string
        * `manually_trusted_key`
            * `require_initial_manual_trust` : boolean
        * `non_verifying`

Supported `availability` : `type` and extra fields needed for it:
* `always`
* `schedule`
    * `startup_schedule` : string
    * `scheduled_uptime` : integer
    * `keep_up_when_active` : boolean
* `demand`
    * `in_demand_delay` : integer
    * `idle_delay` : integer

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
