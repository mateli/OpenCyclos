Behaviour.register({

	'img.serviceClientDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editServiceClient?clientId=" + img.getAttribute("clientId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeServiceClient?clientId=" + img.getAttribute("clientId");
			}
		}
	},
	
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editServiceClient"
		}
	}
});
