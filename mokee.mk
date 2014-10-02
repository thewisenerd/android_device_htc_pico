# Boot animation
TARGET_SCREEN_HEIGHT := 320
TARGET_SCREEN_WIDTH  := 480

# Include GSM stuff
$(call inherit-product, vendor/mk/config/gsm.mk)

DEVICE_PACKAGE_OVERLAYS += device/htc/pico/overlay

# Inherit some common cyanogenmod stuff.
$(call inherit-product, vendor/mk/config/common_mini_phone.mk)
$(call inherit-product, device/common/gps/gps_eu_supl.mk)

# Inherit device configuration
$(call inherit-product, device/htc/pico/full_pico.mk)

PRODUCT_RELEASE_NAME := Explorer

# Product name
PRODUCT_NAME := mk_pico
PRODUCT_BRAND := HTC
PRODUCT_DEVICE := pico
PRODUCT_MODEL := Explorer A310e
PRODUCT_MANUFACTURER := HTC
PRODUCT_BUILD_PROP_OVERRIDES += PRODUCT_NAME=pico BUILD_ID=KTU84Q BUILD_FINGERPRINT=htc_europe/htc_pico/pico:4.4.4/KTU84Q/87995:user/release-keys
PRIVATE_BUILD_DESC="1.28.401.1 CL207463 release-keys"
