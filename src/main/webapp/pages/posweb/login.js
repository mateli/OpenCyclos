
Behaviour.register({

	'#cyclosMember': function(input) {
		Event.observe(input, "keypress", function(event) {
			if (typedCode(event) == Event.KEY_RETURN) {
				Event.stop(event);
				setFocus('cyclosUsername');
			}			
		});
	},

	'#cyclosUsername': function(input) {
		Event.observe(input, "keypress", function(event) {
			if (typedCode(event) == Event.KEY_RETURN) {
				Event.stop(event);
				setFocus('cyclosPassword');
			}			
		});
	}
});

Event.observe(self, 'load', function() {
	try {
		setFocus('cyclosMember');
	} catch (e) {
		setFocus('cyclosUsername');
	}
});