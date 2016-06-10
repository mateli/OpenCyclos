Behaviour.register({
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editExternalAccount";
		}
	},
	
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editExternalAccount?externalAccountId=" + img.getAttribute("externalAccountId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/removeExternalAccount?externalAccountId=" + img.getAttribute("externalAccountId");
			}
		}
	}
});