-- Categories
INSERT INTO categories (name, description) VALUES
('Home Appliances', 'Kitchen and household electrical appliances'),
('Electronics', 'Consumer electronics and gadgets'),
('HVAC Systems', 'Heating, ventilation, and air conditioning systems'),
('Power Tools', 'Electric tools and equipment'),
('Lighting', 'Indoor and outdoor lighting systems');

-- Vendors
INSERT INTO vendors (name, contact_info, category_id) VALUES
-- Home Appliances Vendors
('WhirlTech Solutions', 'contact@whirltech.com', 1),
('ElectroHome Services', 'support@electrohome.com', 1),
('AppliancePro', 'info@appliancepro.com', 1),

-- Electronics Vendors
('TechFix Masters', 'service@techfix.com', 2),
('CircuitCare', 'help@circuitcare.com', 2),
('SmartRepair Solutions', 'contact@smartrepair.com', 2),

-- HVAC Vendors
('CoolAir Systems', 'support@coolair.com', 3),
('ClimateControl Experts', 'service@climatecontrol.com', 3),
('ThermalTech Services', 'info@thermaltech.com', 3),

-- Power Tools Vendors
('ToolMaster Pro', 'support@toolmaster.com', 4),
('PowerFix Solutions', 'service@powerfix.com', 4),

-- Lighting Vendors
('LightingPro Services', 'help@lightingpro.com', 5),
('IllumiTech Solutions', 'support@illumitech.com', 5);

-- Products
INSERT INTO products (name, model_number, vendor_id) VALUES
-- Home Appliances Products
('Smart Refrigerator', 'RF-2023-X', 1),
('Washing Machine', 'WM-500', 1),
('Dishwasher', 'DW-3000', 1),
('Electric Oven', 'EO-2023', 2),
('Microwave Oven', 'MW-1000', 2),

-- Electronics Products
('LED TV', 'TV-4K-55', 4),
('Home Theater System', 'HT-7.1', 4),
('Smart Speaker', 'SS-2023', 5),
('Gaming Console', 'GC-PRO', 5),

-- HVAC Products
('Air Conditioner', 'AC-2023', 7),
('Heat Pump', 'HP-500', 7),
('Smart Thermostat', 'ST-100', 8),

-- Power Tools Products
('Electric Drill', 'ED-2000', 10),
('Power Saw', 'PS-3000', 10),
('Impact Driver', 'ID-1000', 11),

-- Lighting Products
('Smart LED Bulb', 'LED-100', 12),
('Ceiling Fan with Light', 'CF-200', 12),
('Outdoor Floodlight', 'OF-300', 13);

-- Malfunctions
INSERT INTO malfunctions (description, repair_price, product_id) VALUES
-- Refrigerator Malfunctions
('Not cooling properly', 150.00, 1),
('Ice maker not working', 100.00, 1),
('Water leakage', 120.00, 1),

-- Washing Machine Malfunctions
('Drum not spinning', 200.00, 2),
('Water not draining', 150.00, 2),
('Excessive noise', 100.00, 2),

-- TV Malfunctions
('No picture display', 180.00, 6),
('No sound', 120.00, 6),
('Power issues', 90.00, 6),

-- Air Conditioner Malfunctions
('Not cooling', 250.00, 10),
('Strange noise', 150.00, 10),
('Water leakage', 120.00, 10),

-- Power Tool Malfunctions
('Motor not working', 130.00, 13),
('Battery not charging', 80.00, 13),
('Overheating', 100.00, 13);
