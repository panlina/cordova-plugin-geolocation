var id = 0;
exports.watchPosition = function (callback, config) {
	cordova.exec(callback, undefined, "Geolocation", "watchPosition", [id, config]);
	return id++;
};
