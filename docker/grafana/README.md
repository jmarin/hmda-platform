
## Instructions for testing generation of Grafana Dashboards

1. Create virtual environment for `Python` (skip if already created)

First install `virtualenvwrapper` if it is not already installed in your system:

```bash
pip install virtualenvwrapper
source /usr/local/bin/virtualenvwrapper.sh
```

And then create virtual environment with `Python` 3 enabled

```bash
mkvirtualenv -p python3 grafana
```

To activate / deactivate this virtual environment: 

Work on the `grafana` virtual environment
```bash
workon grafana
```

Deactivate the `grafana` virtual environment
```bash
deactivate grafana
```

2. Install dependencies: 

```bash
pip install -r requirements.txt
```

3. Generate Grafana dashboars:

```bash
generate-dashboard -o hmda.jvm.json hmda.jvm.dashboard.py
```