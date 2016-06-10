Behaviour.register({
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editCustomField?nature=" + nature;
		}
	},
	'#changeOrderButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/setCustomFieldOrder?nature=" + nature;
		}
	},
	
	'img.fieldDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editCustomField?nature=" + nature + "&fieldId=" + img.getAttribute("fieldId");
		}
	},

	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeCustomField?nature=" + nature + "&fieldId=" + img.getAttribute("fieldId");
			}
		}
	}
});