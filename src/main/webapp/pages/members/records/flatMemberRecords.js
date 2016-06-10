Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			navigateToProfile(currentElementId, elementNature);
		}
	},
	
	'img.edit': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editMemberRecord?memberRecordId=" + img.getAttribute("memberRecordId") + "&global=false";
		}
	},

	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/removeMemberRecord?memberRecordId=" + img.getAttribute("memberRecordId");
			}
		}
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}
});

Event.observe(self, "load", function() {
	//Focus the first field only when not on an anchor (or the page would be scrolled up anyway)
	if (location.href.indexOf("#") < 0) {
		try {
			Form.findFirstElement(document.forms[0]).focus();
		} catch (e) {}
	}
});