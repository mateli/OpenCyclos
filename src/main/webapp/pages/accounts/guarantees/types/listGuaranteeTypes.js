Behaviour.register({
	
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editGuaranteeType?guaranteeTypeId=" + img.getAttribute("guaranteeTypeId");
		}
	},
	
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editGuaranteeType";
		}
	},
	
	'img.remove' : function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeGuaranteeTypeConfirmation)) {		
				self.location = pathPrefix + "/deleteGuaranteeType?guaranteeTypeId=" + img.getAttribute("guaranteeTypeId");
			}
		}
	}

});