function doPrint() {
	self.print();
}

function doClose() {
	self.close();
}

Behaviour.register({
	'#printButton': function(button) {
		button.onclick = doPrint;
	},

	'#closeButton': function(button) {
		button.onclick = doClose;
	}
});

Event.observe(self, "load", function() {
	if (isPosWeb) {
		keyBinding(Event.KEY_ESC, doClose);
		keyBinding(Event.KEY_F4, doPrint);
	}
});