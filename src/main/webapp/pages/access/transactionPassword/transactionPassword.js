Behaviour.register({
	'.virtualKeyboard': function(div) {
		var field = getObject(div.getAttribute("field"));
		if (typeof self.firstVirtualKeyboard == 'undefined') {
			self.firstVirtualKeyboard = false;
			Event.observe(self, "load", function() {
				setFocus(field);
			})
		}
		new VirtualKeyboard(div, field, {
			'chars': chars, 
			'submitLabel': submitLabel, 
			'contrast': contrastLabel, 
			'clear': clearLabel,
			'buttonsPerRow': buttonsPerRow
		});
	}
});
