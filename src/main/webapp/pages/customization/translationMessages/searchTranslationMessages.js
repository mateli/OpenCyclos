Behaviour.register({
	'#removeForm': function(form) {
		form.onsubmit = function() {
			if (getValue("messageIds") == null) {
				alert(noneSelectedMessage);
				return false;
			}
			return confirm(removeSelectedConfirmation);
		}
	},

	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editTranslationMessage";
		}
	},
	
	'img.messageDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editTranslationMessage?messageId=" + img.getAttribute("messageId");
		}
	},
		
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeOneConfirmation)) {
				self.location = pathPrefix + "/removeTranslationMessages?messageIds=" + img.getAttribute("messageId");
			}
		}
	},
	
	'#selectAllButton': function(button) {
		button.onclick = checkAll.bind(self, "messageIds", true);
	},
	
	'#selectNoneButton': function(button) {
		button.onclick = checkAll.bind(self, "messageIds", false);
	}
});