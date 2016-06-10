Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			if (getValue("alertIds") == null) {
				alert(noneSelectedMessage);
				return false;
			}
			return confirm(removeSelectedConfirmation);
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeOneConfirmation)) {
				//alert (pathPrefix + "/removeAlerts?alertIds=" + img.getAttribute("alertId") + "&alertType=" + getValue('alertType'));
				self.location = pathPrefix + "/removeAlerts?alertIds=" + img.getAttribute("alertId") + "&alertType=" + getValue('alertType');
			}
		}
	},
	
	'#selectAllButton': function(button) {
		button.onclick = checkAll.bind(self, "alertIds", true);
	},
	
	'#selectNoneButton': function(button) {
		button.onclick = checkAll.bind(self, "alertIds", false);
	}
});