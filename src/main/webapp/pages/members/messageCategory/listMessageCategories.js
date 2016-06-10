Behaviour.register({
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editMessageCategory";
		}
	},
	
	'img.messageCategoryDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editMessageCategory?messageCategoryId=" + img.getAttribute("messageCategoryId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeMessageCategory?messageCategoryId=" + img.getAttribute("messageCategoryId");
			}
		}
	}
});