const std = @import("std");

pub fn main() !void {
    const file = try std.fs.cwd().openFile("testinput/d01.txt", .{});
    defer file.close();

    var buffer: [1024]u8 = undefined;
    const reader = file.reader();

    var left_list = std.ArrayList(u32).init(std.heap.page_allocator);
    defer left_list.deinit();
    var right_list = std.ArrayList(u32).init(std.heap.page_allocator);
    defer right_list.deinit();

    while (try reader.readUntilDelimiterOrEof(&buffer, '\n')) |line| {
        var numbers = std.mem.tokenize(u8, line, " \t");

        const left = try std.fmt.parseInt(u32, numbers.next().?, 10);
        const right = try std.fmt.parseInt(u32, numbers.next().?, 10);

        try left_list.append(left);
        try right_list.append(right);
    }

    std.mem.sort(u32, left_list.items, {}, std.sort.asc(u32));
    std.mem.sort(u32, right_list.items, {}, std.sort.asc(u32));

    var total_diff: u32 = 0;
    for (left_list.items, right_list.items) |left, right| {
        const diff = if (left > right) left - right else right - left;
        total_diff += diff;
    }

    const stdout = std.io.getStdOut().writer();
    try stdout.print("Total difference: {}\n", .{total_diff});
}
