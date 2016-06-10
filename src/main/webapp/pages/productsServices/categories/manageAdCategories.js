Behaviour.register({
	'#importForm': function(form) {
		form.onsubmit = function() {
			if (isEmpty(getValue("upload"))) {
				alert(noFileMessage);
				return false;
			}
			var confirmed = confirm(confirmImportMessage);
			if (confirmed) {
				showMessageDiv();
			}
			return confirmed;
		}
	},
		
	'#exportButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/exportAdCategories";
		}
	}
});