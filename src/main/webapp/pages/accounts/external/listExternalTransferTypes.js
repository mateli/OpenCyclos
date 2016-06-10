Behaviour.register({
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editExternalTransferType?account="+externalAccountId;
		}
	},
	
	'img.typeDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editExternalTransferType?externalTransferTypeId=" + img.getAttribute("externalTransferTypeId");
		}
	},
	
	'img.typeRemove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/removeExternalTransferType?externalTransferTypeId=" + img.getAttribute("externalTransferTypeId");
			}
		}
	}
});