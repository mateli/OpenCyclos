var VirtualKeyboard = Class.create();
VirtualKeyboard.contrastClasses = [
	"virtualKeyboardContrastVeryLow", 
	"virtualKeyboardContrastLow", 
	"virtualKeyboardContrastNormal", 
	"virtualKeyboardContrastHigh", 
	"virtualKeyboardContrastVeryHigh"
];
Object.extend(VirtualKeyboard.prototype, {

	initialize: function(container, field, options) {
		this.container = $(container);
		this.field = getObject(field);
		this.options = options || {};
		if (typeof this.options.chars == "string") {
			this.options.chars = this.options.chars.split("");
		}
		this.options.buttonsPerRow = this.options.buttonsPerRow || 5;
		this.options.buttonMargin = this.options.buttonMargin || "1px";
		this.options.capsLock = this.options.capsLock || "_caps_lock_";
		this.options.full = this.options.full || "_full_";
		this.options.clear = this.options.clear || "_clear_";
		this.options.contrast = this.options.contrast || "_contrast_";
		this.lowercase = true;
		this.contrast = 2;
		this.render();
	},
	
	isSpecificChars: function() {
		return this.options.chars != null;
	},
	
	render: function() {
		this.container.innerHTML = "";
		this.createButtonContainer();
		this.createActionContainer();
		var specificChars = this.isSpecificChars();
		if (specificChars) {
			//Render random characters (like transaction password)
			var chars = shuffle(this.options.chars);
			for (var i = 0; i < chars.length; i++) {
				this.createButton(chars[i]);
				if ((i + 1) % this.options.buttonsPerRow == 0) {
					this.createBreak();
				}
			}
		} else {
			//Render a whole keyboard
			var chars = [['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'],
        				 ['q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'],
        				 ['a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'],
        				 ['z', 'x', 'c', 'v', 'b', 'n', 'm']];
        	for (var i = 0; i < chars.length; i++) {
        		var row = chars[i];
        		for (var j = 0; j < row.length; j++) {
        			this.createButton(row[j]);
        		}
        		this.createBreak();
        	}
		}
		var sb = new StringBuffer();
		sb.append("<table cellpadding='0' cellspaging='0' align='center'>");
		sb.append("<tr><td align='center' style='padding-top:5px'>");
			sb.append("<input type='button' class='button contrast' style='width:20px;padding-left:5px;padding-right:5px;' value='-'><span style='padding:0px 10px'>");
			sb.append(this.options.contrast);
			sb.append("</span><input type='button' class='button contrast' style='width:20px;padding-left:5px;padding-right:5px;' value='+'>");
		sb.append("</td></tr><tr><td align='center' style='padding-top:5px'>");
		var buttonStyle = "style='width:90px'";
		if (!specificChars) {
			sb.append("<input type='button' class='button capsLock' value='" + this.options.capsLock + "' " + buttonStyle + ">&nbsp;");
		}
		if (this.options.showFull) {
			sb.append("<input type='button' class='button showFullKeyboard' value='" + this.options.full + "' " + buttonStyle + ">&nbsp;");
		}
		sb.append("<input type='button' class='button clearField' value='" + this.options.clear + "' " + buttonStyle + ">");
		if (this.options.submitLabel != null) {
			sb.append("&nbsp;<input type='submit' class='button' value='" + this.options.submitLabel + "' " + buttonStyle + ">");
		}
		sb.append("</td></tr></table>");
		this.actionContainer.innerHTML = sb;
		this.applyActions();
	},
	
	createButton: function(character) {
		var button = document.createElement("input");
		button.className = "virtualKeyboardButton";
		if (this.options.buttonStyle) {
			button.style.cssText = this.options.buttonStyle;
		}
		button.setAttribute("type", "button");
		button.setAttribute("value", character);
		button = this.buttonContainer.appendChild(button);
		button.virtualKeyboard = this;
		button.style.marginRight = this.options.buttonMargin;
		button.style.marginBottom = this.options.buttonMargin;
		this.applyContrast(button);
		setPointer(button);
		addClassOnHover(button, "virtualKeyboardButtonHover");
		if (!this.isSpecificChars()) {
			Event.observe(button, "mouseover", function(event) {
				if (event.shiftKey) {
					this._oldValue = this.value;
					if (button.virtualKeyboard.lowercase) {
						this.value = this.value.toUpperCase();
					} else {
						this.value = this.value.toLowerCase();
					}
				}
			}.bindAsEventListener(button));
			Event.observe(button, "mouseout", function(event) {
				if (this._oldValue) {
					this.value = this._oldValue;
					try {
						delete this._oldValue;
					} catch (e) {}
				}
			}.bindAsEventListener(button));
		}
		Event.observe(self, "unload", function() {
			button.virtualKeyboard = null;
		});
	},
	
	createBreak: function() {
   		this.buttonContainer.appendChild(document.createElement("br"));
	},
	
	createButtonContainer: function() {
		var div = document.createElement("div");
		div = this.container.appendChild(div);
		div.style.textAlign = "center";
		div.style.paddingTop = this.options.buttonMargin;
		div.style.paddingLeft = this.options.buttonMargin;
		this.buttonContainer = div;
	},
	
	createActionContainer: function() {
		var div = document.createElement("div");
		div = this.container.appendChild(div);
		div.style.textAlign = "center";
		div.style.paddingTop = this.options.buttonMargin;
		div.style.paddingLeft = this.options.buttonMargin;
		this.actionContainer = div;
	},
	
	applyContrast: function(button) {
		for (var i = 0; i < VirtualKeyboard.contrastClasses.length; i++) {
			Element.removeClassName(button, VirtualKeyboard.contrastClasses[i]);
		}
		Element.addClassName(button, VirtualKeyboard.contrastClasses[this.contrast]);
	},
	
	applyActions: function() {
		var buttons = this.buttonContainer.getElementsByTagName("input");
		var virtualKeyboard = this;
		for (var i = 0; i < buttons.length; i++) {
			var button = buttons.item(i);
			button.onclick = function() {
				virtualKeyboard.field.value += this.value;
			}
		}
		
		var actions = this.actionContainer.getElementsByTagName("input");
		for (var i = 0; i < actions.length; i++) {
			var action = actions.item(i);
			action.onclick = function() {
				if (Element.hasClassName(this, "capsLock")) {
					virtualKeyboard.lowercase = !virtualKeyboard.lowercase;
					for (var i = 0; i < buttons.length; i++) {
						var button = buttons.item(i);
						button.value = button.value[virtualKeyboard.lowercase ? 'toLowerCase' : 'toUpperCase']();
					}
				} else if (Element.hasClassName(this, "showFullKeyboard")) {
					virtualKeyboard.options.showFull = false;
					virtualKeyboard.options.chars = null;
					virtualKeyboard.container.innerHTML = "";
					new VirtualKeyboard(virtualKeyboard.container, virtualKeyboard.field, virtualKeyboard.options);
				} else if (Element.hasClassName(this, "clearField")) {
					virtualKeyboard.field.value = "";
				} else if (Element.hasClassName(this, "contrast")) {
					var more = this.value == "+";
					if (more && virtualKeyboard.contrast < VirtualKeyboard.contrastClasses.length - 1) {
						virtualKeyboard.contrast++;
					} else if (!more && virtualKeyboard.contrast > 0) {
						virtualKeyboard.contrast--;
					}
					for (var i = 0; i < buttons.length; i++) {
						var button = buttons.item(i);
						virtualKeyboard.applyContrast(button);
					}
				}
			}
		}
	}
});