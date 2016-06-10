Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			if (getValue("adInterestsIds") == null) {
				alert(noneSelectedMessage);
				return false;
			}
			return confirm(removeSelectedConfirmation);
		}
	},
	
	'img.edit': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editAdInterest?adInterest(id)=" + img.getAttribute("adInterestId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeOneConfirmation)) {
				self.location = pathPrefix + "/removeAdInterests?adInterestsIds=" + img.getAttribute("adInterestId");
			}
		}
	},
	
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editAdInterest";
		}
	},
	
	'#selectAllButton': function(button) {
		button.onclick = checkAll.bind(self, "adInterestsIds", true);
	},
	
	'#selectNoneButton': function(button) {
		button.onclick = checkAll.bind(self, "adInterestsIds", false);
	}
});