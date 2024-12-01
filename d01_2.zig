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

    var total_similarity: u32 = 0;
    for (left_list.items) |left| {
        var count: u32 = 0;
        for (right_list.items) |right| {
            if (right == left) {
                count += 1;
            }
        }
        total_similarity += left * count;
    }

    const stdout = std.io.getStdOut().writer();
    try stdout.print("Total similarity: {}\n", .{total_similarity});
}
