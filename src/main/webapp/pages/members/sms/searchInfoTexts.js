Behaviour.register({
	
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editInfoText?infoTextId=" + img.getAttribute("infoTextId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeInfoText?infoTextId=" + img.getAttribute("infoTextId");
			}
		}
	}
});