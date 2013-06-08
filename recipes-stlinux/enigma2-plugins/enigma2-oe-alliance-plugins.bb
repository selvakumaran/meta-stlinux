DESCRIPTION = "Additional plugins for Enigma2"
MAINTAINER = "oe-alliance team"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

PROVIDES = "${PN} \
	enigma2-plugin-systemplugins-autobouquetsmaker \
	enigma2-plugin-systemplugins-blindscan \
	enigma2-plugin-extensions-dlnabrowser \
	enigma2-plugin-extensions-dlnaserver \
	enigma2-plugin-systemplugins-firmwareupgrade \
	enigma2-plugin-systemplugins-fpgaupgrade \
	enigma2-plugin-systemplugins-vfdcontrol \
	enigma2-plugin-extensions-streamtv \
	enigma2-plugin-systemplugins-tempfancontrol \
	enigma2-plugin-systemplugins-fancontrol \
	enigma2-plugin-extensions-vuplusevent \
	enigma2-plugin-systemplugins-remotecontrolcode \
	enigma2-plugin-extensions-ondemand-openuitzendinggemist \
	enigma2-plugin-extensions-tunerserver \
	enigma2-plugin-extensions-hbbtv \
	enigma2-plugin-systemplugins-transcodingsetup \
	enigma2-plugin-systemplugins-micomupgrade \
	enigma2-plugin-extensions-ondemand \
	enigma2-plugin-extensions-fempa \
	"

DESCRIPTION_enigma2-plugin-systemplugins-autobouquetsmaker = "Automatically build and update bouquets from the satellite stream."
RREPLACES_enigma2-plugin-systemplugins-autobouquetsmaker = "enigma2-plugin-extensions-autobouquets"
RCONFLICTS_enigma2-plugin-systemplugins-autobouquetsmaker = "enigma2-plugin-extensions-autobouquets"
DESCRIPTION_enigma2-plugin-systemplugins-blindscan = "blindscan..."
RDEPENDS_enigma2-plugin-systemplugins-blindscan = "virtual/blindscan-dvbs"
DESCRIPTION_enigma2-plugin-extensions-dlnabrowser = "this is dlna/upnp browser using djmount"
RDEPENDS_enigma2-plugin-extensions-dlnabrowser = "djmount fuse-utils libfuse2 libupnp3 gst-plugins-bad-neonhttpsrc"
DESCRIPTION_enigma2-plugin-extensions-dlnaserver = "this is dlna server using minidlna"
RDEPENDS_enigma2-plugin-extensions-dlnaserver = "minidlna"
DESCRIPTION_enigma2-plugin-systemplugins-firmwareupgrade = "Upgrade your system Firmware"
DESCRIPTION_enigma2-plugin-systemplugins-fpgaupgrade = "Upgrade your system FPGA"
DESCRIPTION_enigma2-plugin-systemplugins-vfdcontrol = "vfd controller"
DESCRIPTION_enigma2-plugin-extensions-streamtv = "iptv player"
RDEPENDS_enigma2-plugin-extensions-streamtv = "librtmp"
DESCRIPTION_enigma2-plugin-systemplugins-tempfancontrol = "Control your internal system fan."
DESCRIPTION_enigma2-plugin-systemplugins-fancontrol = "Control your internal system fan."
RDEPENDS_enigma2-plugin-systemplugins-fancontrol_et9x00 = "hddtemp"
DESCRIPTION_enigma2-plugin-extensions-vuplusevent = "Return the Love Event (only for genuine box)"
DESCRIPTION_enigma2-plugin-systemplugins-remotecontrolcode = "Change Remote Control Code"
DESCRIPTION_enigma2-plugin-extensions-ondemand-openuitzendinggemist = "Watch NL-IP TV"
DESCRIPTION_enigma2-plugin-extensions-tunerserver = "Builds a virtual channels list"
DESCRIPTION_enigma2-plugin-extensions-hbbtv = "HbbTV player"
RDEPENDS_enigma2-plugin-extensions-hbbtv = "tslib-conf libts-1.0-0 libsysfs2 libgmp10 libmpfr4 vuplus-opera-browser-util enigma2-hbbtv-util"
DESCRIPTION_enigma2-plugin-systemplugins-transcodingsetup = "Setup transcoding of your VU+"
RDEPENDS_enigma2-plugin-systemplugins-transcodingsetup = "vuplus-transtreamproxy"
DESCRIPTION_enigma2-plugin-systemplugins-micomupgrade = "micomupgrade"
RDEPENDS_enigma2-plugin-extensions-ondemand = "python-dnspython python-beautifulsoup python-lxml python-simplejson python-pyamf"
DESCRIPTION_enigma2-plugin-extensions-ondemand = "Watch on demand TV."
DESCRIPTION_enigma2-plugin-extensions-fempa = "Norwegian P4 FEM PAA radio show player."

DEPENDS = "enigma2 \
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbc", "virtual/blindscan-dvbc" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "virtual/blindscan-dvbs" , "", d)} \
	python-dnspython python-beautifulsoup python-lxml python-simplejson python-pyamf \
	djmount \
	librtmp \
	minidlna \
	hddtemp \
	ppp \
	usbmodeswitch \
	usbmodeswitch-data \
	wvstreams \
	usbutils \
	gmp \
	tslib \
	mpfr \
	"

inherit gitpkgv autotools

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r48"

SRC_URI="git://github.com/oe-alliance/oe-alliance-plugins.git;protocol=git \
	file://oe-alliance-plugins-spark.patch;patch=1 \
"

EXTRA_OECONF = " \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	--with-po \
	--with-boxtype=${MACHINE}"

ALLOW_EMPTY_${PN} = "1"
PACKAGES += "${PN}-meta"
FILES_${PN}-meta = "${datadir}/meta"

S = "${WORKDIR}/git"

python populate_packages_prepend() {
	enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
	do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}
