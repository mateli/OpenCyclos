Behaviour.register({
    '#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/overviewExternalAccounts";
		}
	},
	
	'#newImportForm': function(form) {
		form.onsubmit = function() {
			if (isEmpty(getValue("file"))) {
				alert(noFileMessage);
				return false;
			}
			return true;
		}
	},
	
	'img.view': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/externalAccountHistory?transferImportId=" + img.getAttribute("transferImportId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/removeTransferImport?transferImportId=" + img.getAttribute("transferImportId");
			}
		}
	}
});