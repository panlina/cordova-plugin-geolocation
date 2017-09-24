var id = 0;
exports.watchPosition = function (callback, config) {
	cordova.exec(callback, undefined, "GeolocationNative", "watchPosition", [id, config]);
	return id++;
};
