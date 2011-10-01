DESCRIPTION = "Meet Cloud9, development-as-a-service for Javascripters and other developers"
HOMEPAGE = "http://c9.io"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4784c3bcff601fd8f9515f52a11e7018"
DEPENDS = "rsync"
RDEPENDS = "nodejs"

SRC_URI = "git://github.com/jadonk/cloud9.git;protocol=git \
"

SRCREV = "08bae1d1cc2ba9f7f883a25afd07f0339a82fa8b"
S = "${WORKDIR}/git"

# Most of cloud9 is in git submodules that still need to be fetched.
do_configure_prepend () {
 git submodule update --init --recursive
}

# There's nothing left to be compiled at this time.
# node-o3-xml is the only compiled code module in here.
# The repository has binaries for:
#  Linux ARM armv7
#  Linux x86 32/64-bit
#  Sun Solaris 32-bit
#  Mac x86 32/64-bit
#  Windows x86 32-bit
do_compile () {
:
}

do_install () {
 install -m 0755 -d ${D}/usr/share/cloud9 ${D}${bindir}
 rsync -r --exclude=".*" ${S}/* ${D}/usr/share/cloud9
 touch ${D}${bindir}/cloud9
 echo "#!/bin/sh" > ${D}${bindir}/cloud9
 echo "node /usr/share/cloud9/bin/cloud9.js -l 0.0.0.0 -w /home/root -p 3000" >> ${D}${bindir}/cloud9
 chmod 0755 ${D}${bindir}/cloud9
}
