Behaviour.register({
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editMessageSetting?setting=" + img.getAttribute("setting");
		}
	}
});