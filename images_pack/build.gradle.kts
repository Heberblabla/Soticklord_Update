plugins {
    id("com.android.asset-pack")
}

assetPack {
    packName.set("images_pack") // Can be different, but should be unique
    dynamicDelivery {
        deliveryType.set("install-time") // Or "fast-follow", or "on-demand"
    }

}