exports.watchPosition = function (callback, config) {
	cordova.exec(callback, undefined, "Geolocation", "watchPosition", [config]);
};
