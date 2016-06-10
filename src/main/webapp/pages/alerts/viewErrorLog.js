Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			if (getValue("entryIds") == null) {
				alert(noneSelectedMessage);
				return false;
			}
			return confirm(removeSelectedConfirmation);
		}
	},
	
	'img.view': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/viewErrorLogEntry?entryId=" + img.getAttribute("entryId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeOneConfirmation)) {
				self.location = pathPrefix + "/removeErrorLogEntries?entryIds=" + img.getAttribute("entryId");
			}
		}
	},
	
	'#selectAllButton': function(button) {
		button.onclick = checkAll.bind(self, "entryIds", true);
	},
	
	'#selectNoneButton': function(button) {
		button.onclick = checkAll.bind(self, "entryIds", false);
	}
});