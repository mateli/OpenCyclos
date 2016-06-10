
Behaviour.register({
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/searchMessages";
		}
	},
	
	'#replyButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/sendMessage?inReplyTo=" + messageId;
		}
	},
	
	'input.applyMessageAction': function(button) {
		button.onclick = function() {
			var action = button.getAttribute("messageAction");
			var confirmation = button.getAttribute("confirmation");
			if (!isEmpty(confirmation)) {
				if (!confirm(eval(confirmation))) return false;
			}
			self.location = pathPrefix + "/changeMessageStatus?action=" + action + "&messageId=" + messageId;
		}
	}
});

