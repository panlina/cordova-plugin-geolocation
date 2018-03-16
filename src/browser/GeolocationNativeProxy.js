module.exports = {
	watchPosition: function (onsuccess, onerror) {
		navigator.geolocation.watchPosition(function (position) {
			onsuccess(Position(position));
		}, onerror);
	}
};

function Position(position) {
	return {
		latitude: position.coords.latitude,
		longitude: position.coords.longitude,
		accuracy: position.coords.accuracy,
		timestamp: position.timestamp
	};
}

require("cordova/exec/proxy").add("GeolocationNative", module.exports);
