SmartCardApduPro
================

Sending apdu command to smartcard.

Instruction to configure on Linux
--------------------------------
Install the following packages:
1.- sudo apt install pcsc-tools
2.- sudo apt install pcscd

configure smardcard.io
----------------------
1.- run: ldd -r /usr/bin/pcsc_scan
	Example: libpcsclite.so.1 => /usr/lib/x86_64-linux-gnu/libpcsclite.so.1
2.- java -Dsun.security.smartcardio.library=/usr/lib/x86_64-linux-gnu/libpcsclite.so.1

good luck
