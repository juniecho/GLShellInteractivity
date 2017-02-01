# GomdoLight Shell Interactivity Provider

## Introduction

This app allows an Android custom firmware developer to 
  - Display toast popups and notifications
  - Find out how the device is connected to the Internet (data network or Wi-Fi, or no connection)

from their shell scripts.



## Usage

Note that your shell scripts should run with root privilege

---
### Displaying Notifications
```
VAR_noti_title="TITLE of the message"
VAR_noti_body="BODY of the message"
am broadcast -a gom.dolight.provider.shellinteractivity.Notification -e message "$GLNotiTitle|$GLNotiBody" -n gom.dolight.provider.shellinteractivity/.BootReceiver
```
---
### Displaying Short Toast Messages
```
VAR_toast_text="BODY of the message, NO TITLE"
am broadcast -a gom.dolight.provider.shellinteractivity.Toast -e message "0$VAR_toast_text" -n gom.dolight.provider.shellinteractivity/.BootReceiver
```
---
### Displaying Long Toast Messages
```
VAR_toast_text="BODY of the message, NO TITLE"
am broadcast -a gom.dolight.provider.shellinteractivity.Toast -e message "1$VAR_toast_text" -n gom.dolight.provider.shellinteractivity/.BootReceiver
```
---
### Network Connectivity Check
#### Issue the following command to generate/update/refresh connection status indicators:
```
am broadcast -a gom.dolight.provider.shellinteractivity.ConnectivityCheck -n gom.dolight.provider.shellinteractivity/.NetworkReceiver
```
#### Generated indicator files (if the device is connected):
* On Wi-Fi: ```/data/data/gom.dolight.provider.shellinteractivity/connected_wifi```
* On data network: ```/data/data/gom.dolight.provider.shellinteractivity/connected_data```

#### If no connection or the connection changes:
* If no connection, No file will be generated and any existing indicator will be deleted
* Any indicator for inactive connection type will be deleted each time the above command is executed
* No automatic refresh. If you want automatic refresh add ```<action android:name="android.net.conn.CONNECTIVITY_CHANGE" />``` to the intent-filter.

#### Now check for these files:

```
if [ -e /data/data/gom.dolight.provider.shellinteractivity/connected_wifi ]; then
	# do things what should be done on Wi-Fi
elif [ -e /data/data/gom.dolight.provider.shellinteractivity/connected_data ]; then
	# do things what should be done on data connection
else
	# device is not currently connected to the internet
fi
```

## Open Source Usage

* NaraePreference: https://github.com/PyxisDev/NaraePreference

## License

[MIT](https://github.com/juniecho/GLShellInteractivity/blob/master/LICENSE.md)
